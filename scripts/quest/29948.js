/*
	����: ��������������
	����: ������Ѧ��˹�������˿�������
	��Ҫ: 1142259 - ������������
*/
var status = -1;

function start(mode, type, selection) {
    if (qm.haveItem(1142259, 1) && qm.getPlayer().getLevel() >= 10) {
        qm.startQuest();
        qm.forceCompleteQuest();
    }
    qm.dispose();
}

function end(mode, type, selection) {
    if (qm.haveItem(1142259, 1) && qm.getPlayer().getLevel() >= 10) {
        qm.startQuest();
        qm.forceCompleteQuest();
    }
    qm.dispose();
}