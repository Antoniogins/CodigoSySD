package p4;

public class Tests {
    public static void main(String[] args) {

        System.out.println("                TEST 1: Header");
        Header h = new Header();
        System.out.println(h.type);
        System.out.println(Integer.toBinaryString(h.type));
        System.out.println(Integer.toHexString(h.type));
        System.out.println(Integer.toOctalString(h.type));
        byte[] umlet = h.pack();
        for (byte umlet2 : umlet) {
            System.out.print(umlet2 + " ");
        }
        System.out.println("\n");








        System.out.println("                TEST 2: UserMessage");
        UserMessage um = new UserMessage("antonio", 77854401);
        System.out.println(um.type + " " + um.length + " " + um.name + " " + um.dni);
        byte[] user = um.pack();
        for (byte l : user) {
            System.out.print(l + " ");
        }

        UserMessage uprime = UserMessage.unpack(user);
        System.out.println("\n" + uprime.type + " " + uprime.length + " " + uprime.name + " " + uprime.dni + "\n\n");








        System.out.println("                TEST 3: TextMessage");
        TextMessage tm = new TextMessage("antonio programador hacker termotanque mensaje largo?", 77854401);
        System.out.println(tm.type + " " + tm.length + " \"" + tm.text + "\" " + tm.dni);
        byte[] text = tm.pack();
        for (byte l : text) {
            System.out.print(l + " ");
        }

        TextMessage tmp = TextMessage.unpack(text);
        System.out.println("\n" + tmp.type + " " + tmp.length + " \"" + tmp.text + "\" " + tmp.dni + "\n\n");







        System.out.println("                TEST 4: CloseMessage");
        CloseMessage cm = new CloseMessage();
        System.out.println(cm.type + " " + cm.length);
        byte[] close = cm.pack();
        for (byte l : close) {
            System.out.print(l + " ");
        }

        CloseMessage cmp = CloseMessage.unpack(close);
        System.out.println("\n" + cmp.type + " " + cmp.length + "\n\n");

    }
}
