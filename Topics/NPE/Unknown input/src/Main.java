class Util {
    // correct this method to avoid NPE
    public static void printLength(String name) {
        System.out.println(name == null ? 0 : name.length());
    }
}