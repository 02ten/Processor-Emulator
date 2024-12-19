public class Instructions {

    private int opcode;
    private int dest;
    private int src1;
    private int src2;

    public Instructions(int opcode, int dest, int src1, int src2) {
        this.opcode = opcode;
        this.dest = dest;
        this.src1 = src1;
        this.src2 = src2;
    }

    public int getOpcode() {
        return opcode;
    }

    public int getDest() {
        return dest;
    }

    public int getSrc1() {
        return src1;
    }

    public int getSrc2() {
        return src2;
    }

    public static Instructions fromMachineCode(int machineCode) {
        //TODO Расширить адреса

        int opcode = (machineCode >> 12) & 0x7;
        int dest = (machineCode >> 8) & 0xF;
        int src1 = (machineCode >> 4) & 0xF;
        int src2 = machineCode & 0xF;
        System.out.printf("Операция в бинарном коде: %4s%n", Integer.toBinaryString(opcode));
        System.out.printf("Адрес назначения в бинарном коде: %4s%n", Integer.toBinaryString(dest));
        System.out.printf("Первый адрес в бинарном коде: %4s%n", Integer.toBinaryString(src1));
        System.out.printf("Второй адрес в бинарном коде: %4s%n", Integer.toBinaryString(src2));
        return new Instructions(opcode, dest, src1, src2);
    }



}
