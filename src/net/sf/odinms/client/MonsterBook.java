/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc> 
                       Matthias Butz <matze@odinms.de>
                       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation. You may not use, modify
    or distribute this program under any other version of the
    GNU Affero General Public License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.odinms.client;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.sf.odinms.database.DatabaseConnection;
import net.sf.odinms.server.MapleItemInformationProvider;
import net.sf.odinms.tools.data.output.MaplePacketLittleEndianWriter;
import net.sf.odinms.tools.MonsterBookPacket;

public class MonsterBook implements Serializable {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MonsterBook.class);
    private static final long serialVersionUID = 7179541993413738569L;
    private boolean changed = false;
    private int SpecialCard = 0, NormalCard = 0, BookLevel = 1;
    private Map<Integer, Integer> cards;

    public static boolean isSpecialCard(final int id) {
        return id / 1000 >= 2388;
    }

    public static int getBookLevel(final int level) {
        return (int) ((5 * level) * (level + 1));
    }

    public static int getCardShortId(final int id) {
        return id % 10000;
    }

    public MonsterBook(Map<Integer, Integer> cards) {
        this.cards = cards;

        for (Entry<Integer, Integer> card : cards.entrySet()) {
            if (isSpecialCard(card.getKey())) {

                SpecialCard += card.getValue();
            } else {
                NormalCard += card.getValue();
            }
        }
        calculateLevel();
    }
    
    public int getBookLevel() {
        return BookLevel;
    }

    public Map<Integer, Integer> getCards() {
        return cards;
    }

    public int getTotalCards() {
        return SpecialCard + NormalCard;
    }

    public int getNormalCard() {
        return NormalCard;
    }

    public int getSpecialCard() {
        return SpecialCard;
    }

    public final int getLevelByCard(final int cardid) {
        return cards.get(cardid) == null ? 0 : cards.get(cardid);
    }

    public final static MonsterBook loadCards(final int charid) throws SQLException {
        final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM monsterbook WHERE charid = ? ORDER BY cardid ASC");
        ps.setInt(1, charid);
        final ResultSet rs = ps.executeQuery();
        Map<Integer, Integer> cards = new LinkedHashMap<Integer, Integer>();
        int cardid, level;

        while (rs.next()) {
            cards.put(rs.getInt("cardid"), rs.getInt("level"));
        }
        rs.close();
        ps.close();
        return new MonsterBook(cards);
    }
    
    public boolean checkCardIsExists(final int charid, final int cardid) throws SQLException {
        int count = 0;
        
        final Connection con = DatabaseConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) as c FROM monsterbook WHERE charid = ? AND cardid = ?")) {
            ps.setInt(1, charid);
            ps.setInt(2, cardid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("c");
                }
            }
        } catch (SQLException ex) {
            log.error("查询怪物卡是否已存在出错。", ex);
        }
        
        return count > 0;
    }

    public final void saveCards(final int charid) throws SQLException {
        if (!changed || cards.isEmpty()) {
            return;
        }
        
        final Connection con = DatabaseConnection.getConnection();
        
        for (final Entry<Integer, Integer> pair : cards.entrySet()) {
            int cardId = pair.getKey();
            int cardLevel = pair.getValue();
            boolean isExists = checkCardIsExists(charid, cardId);
            
            if (isExists) {
                PreparedStatement ps = con.prepareStatement("UPDATE monsterbook SET level = ? WHERE charid = ? AND cardid = ?");
                ps.setInt(1, cardLevel);
                ps.setInt(2, charid);
                ps.setInt(3, cardId);
                ps.execute();
                ps.close();
            } else {
                PreparedStatement ps = con.prepareStatement("INSERT INTO monsterbook (charid, cardid, `level`) VALUES (?, ?, 1);");
                ps.setInt(1, charid);
                ps.setInt(2, cardId);
                ps.execute();
                ps.close();
            }
        }
    }

    private void calculateLevel() {
        int Size = NormalCard + SpecialCard;
        BookLevel = 8;

        for (int i = 0; i < 8; i++) {
            if (Size <= getBookLevel(i)) {
                BookLevel = (i + 1);
                break;
            }
        }
    }

    public final void addCardPacket(final MaplePacketLittleEndianWriter mplew) {
        mplew.writeShort(cards.size());

        for (Entry<Integer, Integer> all : cards.entrySet()) {
            mplew.writeShort(getCardShortId(all.getKey())); // Id
            mplew.write(all.getValue()); // Level
        }
    }

    public final void addCharInfoPacket(final int bookcover, final MaplePacketLittleEndianWriter mplew) {
        mplew.writeInt(BookLevel);
        mplew.writeInt(NormalCard);
        mplew.writeInt(SpecialCard);
        mplew.writeInt(NormalCard + SpecialCard);
        mplew.writeInt(MapleItemInformationProvider.getInstance().getCardMobId(bookcover));
    }

    public final void updateCard(final MapleClient c, final int cardid) {
        c.getSession().write(MonsterBookPacket.changeCover(cardid));
    }

    public final void addCard(final MapleClient c, final int cardid) {
        changed = true;
        // c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MonsterBookPacket.showForeginCardEffect(c.getPlayer().getId()), false);

        if (cards.containsKey(cardid)) {
            final int levels = cards.get(cardid);
            if (levels >= 5) {
                c.getSession().write(MonsterBookPacket.addCard(true, cardid, levels));
            } else {
                if (isSpecialCard(cardid)) {
                    SpecialCard += 1;
                } else {
                    NormalCard += 1;
                }
                c.getSession().write(MonsterBookPacket.addCard(false, cardid, levels));
                c.getSession().write(MonsterBookPacket.showGainCard(cardid));
                // c.getSession().write(MaplePacketCreator.showSpecialEffect(13));
                cards.put(cardid, levels + 1);
                calculateLevel();
            }
            return;
        }
        if (isSpecialCard(cardid)) {
            SpecialCard += 1;
        } else {
            NormalCard += 1;
        }
        // New card
        cards.put(cardid, 1);
        c.getSession().write(MonsterBookPacket.addCard(false, cardid, 1));
        c.getSession().write(MonsterBookPacket.showGainCard(cardid));
        //   c.getSession().write(MaplePacketCreator.showSpecialEffect(13));
        calculateLevel();
    }
}
