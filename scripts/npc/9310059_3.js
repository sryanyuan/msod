var status = 0;
var itemList = 
Array(     
			Array(2040017,800,1,1), //ͷ�������ʾ���60%
			Array(2040105,800,1,1), //����װ�λر��ʾ���10%
			Array(2040106,800,1,1), //����װ�λر��ʾ���60%
			Array(2040200,800,1,1), //�۲�װ�������ʾ���10%
			Array(2040201,800,1,1), //�۲�װ�������ʾ���60%
			Array(2040205,800,1,1), //�۲�װ����������10%
			Array(2040206,800,1,1), //�۲�װ����������60%		
			Array(2040301,800,1,1), //������������ 60
			Array(2040302,800,1,1), //������������ 10
			Array(2040501,800,1,1), //ȫ���������ݾ���60
			Array(2040513,800,1,1), //ȫ��������������60
			Array(2040613,800,1,1), //��ȹ���ݾ���60%
			Array(2040704,800,1,1), //Ь����Ծ����60
			Array(2040705,800,1,1), //Ь����Ծ����10
			Array(2040804,800,1,1), //���׹�������60
			Array(2040805,800,1,1), //���׹�������10
			Array(2040816,800,1,1), // ����ħ������10%
			Array(2040817,800,1,1), //����ħ������60%
			Array(2040914,800,1,1), // ���ƹ�������60%
			Array(2041013,800,1,1), //������������60
			Array(2041016,800,1,1), //������������60
			Array(2041019,800,1,1), //�������ݾ���60
			Array(2041022,800,1,1), //������������60
			Array(2043002,800,1,1), //���ֽ�10
			Array(2043001,800,1,1), //���ֽ�60
			Array(2044002,800,1,1), //˫�ֽ�10
			Array(2044001,800,1,1), //˫�ֽ�60
			Array(2044302,800,1,1), //ǹ10
			Array(2044301,800,1,1), //ǹ60
			Array(2044502,800,1,1), //��10
			Array(2044501,800,1,1), //��60
			Array(2044602,800,1,1), //��10
			Array(2044601,800,1,1), //��60
			Array(2044702,800,1,1), //ȭ��10
			Array(2044701,800,1,1), //ȭ��60
			Array(2043802,800,1,1), //����10
			Array(2043801,800,1,1), //����60
			Array(2043702,800,1,1), //����10
			Array(2043701,800,1,1), //����60
			Array(1302064,800,1,1), //��Ҷ��64
			Array(1332055,800,1,1), //��
			Array(1332056,800,1,1), //��
			Array(1382076,800,1,1), //��
			Array(1402039,800,1,1), //˫��
			Array(1432040,800,1,1), //ǹ
			Array(1452045,800,1,1), //��
			Array(1472055,800,1,1), //ȭ
			Array(3010044,700,1,1), //��ɡ����
			Array(3010036,800,1,1), //��ǧ
			Array(3010049,800,1,1), //ѧ����
			Array(3010110,800,1,1), //��
			Array(3010131,800,1,1), //��è
			Array(1022129,800,1,1), //�۾�
			Array(3012001,800,1,1), //gouhuo 
			Array(3012002,800,1,1), //yutong
			Array(3012003,800,1,1), //yutong
			Array(1072369,800,1,1), //��ˮ��Ь��
			Array(1022073,800,1,1), //�����۾�
			Array(1142561,700,1,1), //��
			Array(1142562,700,1,1), //�м�
			Array(1142563,700,1,1), //С��
			Array(1142564,700,1,1), //��
			Array(1142565,700,1,1), //ĩ��
			Array(1142566,700,1,1), //��
			Array(1142567,700,1,1), //����
			Array(1092029,700,1,1) //��Ź��
);

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.sendOk("��߸�����Ҫ����������#v1142561#��#v1472055#��ְҵ64��Ҷ������#v1022129#�ӹ��������۾���#v3010044#��Ʒ���ӣ�ͬһ��ɡ�ȣ�.�Ƚ���.");
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        if (cm.haveItem(4170005, 1)) {
            cm.sendYesNo("��߸�����Ҫ����������#v1142561#��#v1472055#��ְҵ64��Ҷ������#v1022129#�ӹ��������۾���#v3010044#��Ʒ���ӣ�ͬһ��ɡ�ȣ�.�Ƚ���.");
        } else {
            cm.sendOk("��߸�����Ҫ����������#v1142561#��#v1472055#��ְҵ64��Ҷ������#v1022129#�ӹ��������۾���#v3010044#��Ʒ���ӣ�ͬһ��ɡ�ȣ�.�Ƚ���.�㱳������1��#b#t4170005##k��?");
            cm.dispose();
        }
    } else if (status == 1) {
        var chance = Math.floor(Math.random() * 900);
        var finalitem = Array();
        for (var i = 0; i < itemList.length; i++) {
            if (itemList[i][1] >= chance) {
                finalitem.push(itemList[i]);
            }
        }
        if (finalitem.length != 0) {
            var item;
            var random = new java.util.Random();
            var finalchance = random.nextInt(finalitem.length);
            var itemId = finalitem[finalchance][0];
            var quantity = finalitem[finalchance][2];
            var notice = finalitem[finalchance][3];
            item = cm.gainGachaponItem(itemId, quantity, "��߸��������齱", notice);
            if (item != -1) {
                cm.gainItem(4170005, -1);
                cm.sendOk("������ #b#t" + item + "##k " + quantity + "����");
            } else {
                cm.sendOk("��ȷʵ��#b#t4170005##k������ǣ�����ȷ���ڱ�����װ�������ģ������������Ƿ���һ�����ϵĿռ䡣");
            }
            cm.dispose();
        } else {
            cm.sendOk("�������������ʲô��û���õ���");
            cm.gainItem(4170005, -1);
            cm.dispose();
        }
    }
}
