/*
	����: ð����ʿ�ŵļ̳���
	����: ð����ʿ�ŵļ̳����ռ�ð�ռҵ����ˡ�
	��Ҫ: 1142257 - ð����ʿ�̳���
*/
var status = -1;

function start(mode, type, selection) {
    if (qm.haveItem(1142257, 1) && qm.getPlayer().getLevel() >= 10) {
        qm.startQuest();
        qm.forceCompleteQuest();
    }
    qm.dispose();
}

function end(mode, type, selection) {
    if (qm.haveItem(1142257, 1) && qm.getPlayer().getLevel() >= 10) {
        qm.startQuest();
        qm.forceCompleteQuest();
    }
    qm.dispose();
}