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

package net.sf.odinms.scripting.quest;

import net.sf.odinms.client.MapleClient;
import net.sf.odinms.scripting.npc.NPCConversationManager;
import net.sf.odinms.server.quest.MapleQuest;

/**
 *
 * @author RMZero213
 */
public class QuestActionManager extends NPCConversationManager {

    private boolean start;
    private int quest;

    public QuestActionManager(MapleClient c, int npc, int quest, boolean start) {
        super(c, npc);
        this.quest = quest;
        this.start = start;
    }

    public int getQuest() {
        return quest;
    }

    public boolean isStart() {
        return start;
    }

    @Override
    public void dispose() {
        QuestScriptManager.getInstance().dispose(this, getClient());
    }

    public void startQuest() {
        MapleQuest.getInstance(quest).start(getPlayer(), getNpc(), true);
    }

    public void completeQuest() {
        MapleQuest.getInstance(quest).complete(getPlayer(), getNpc(), true);
    }
    
    public void forceCompleteQuest() {
        MapleQuest.getInstance(quest).forceComplete(getPlayer(), 0);
    }
    
    @Override
    public void forceCompleteQuest(final int id) {
        MapleQuest.getInstance(id).forceComplete(getPlayer(), 0);
    }
}