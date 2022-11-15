package fumo;

public class FumoSkill
{
    public static int FM(final String a) {
        if (null != a) {
            switch (a) {
                case "强攻": {
                    return 1;
                }
                case "超强攻": {
                    return 2;
                }
                case "战争意志": {
                    return 3;
                }
                case "鹰眼": {
                    return 4;
                }
                case "锐眼": {
                    return 5;
                }
                case "谢幕": {
                    return 6;
                }
                case "兵不血刃": {
                    return 7;
                }
                case "致命打击": {
                    return 8;
                }
                case "蒙蔽": {
                    return 9;
                }
                case "追击": {
                    return 10;
                }
                case "坚韧": {
                    return 21;
                }
                case "坚不可摧": {
                    return 22;
                }
                case "顽固": {
                    return 23;
                }
                case "未卜先知": {
                    return 24;
                }
                case "幸运狩猎": {
                    return 31;
                }
                case "苦中作乐": {
                    return 32;
                }
                case "闲来好运": {
                    return 33;
                }
                case "财源滚滚": {
                    return 34;
                }
                case "异常抗性": {
                    return 100;
                }
                case "异常免疫": {
                    return 101;
                }
                case "伺机待发": {
                    return 200;
                }
                case "茁壮生命": {
                    return 300;
                }
                case "茁壮魔力": {
                    return 301;
                }
                case "茁壮生长": {
                    return 302;
                }
                case "拔苗助长": {
                    return 303;
                }
                case "稳如泰山": {
                    return 400;
                }
                case "愤怒之火": {
                    return 401;
                }
                case "训练有方": {
                    return 500;
                }
                case "训练有素": {
                    return 501;
                }
                case "迅捷突袭": {
                    return 502;
                }
                case "心有灵犀": {
                    return 503;
                }
                case "落叶斩": {
                    return 4211002;
                }
                case "多重飞镖": {
                    return 4111005;
                }
                case "斗气集中": {
                    return 1111002;
                }
                case "属性攻击": {
                    return 1211002;
                }
                case "龙之献祭": {
                    return 1311005;
                }
                case "末日烈焰": {
                    return 2111002;
                }
                case "落雷枪": {
                    return 2211003;
                }
                case "圣光": {
                    return 2311004;
                }
                case "箭扫射1": {
                    return 3111006;
                }
                case "箭扫射2": {
                    return 3211006;
                }
                case "超人变身": {
                    return 5111005;
                }
                case "双枪喷射": {
                    return 5211004;
                }
            }
        }
        return 0;
    }
}
