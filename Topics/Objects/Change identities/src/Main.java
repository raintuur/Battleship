
class Person {
    String name;
    int age;
}

class MakingChanges {

    public static void changeIdentities(Person p1, Person p2) {
        Person temp = createTempPersonFrom(p1);
        copyIdentityDataFromTo(p2, p1);
        copyIdentityDataFromTo(temp, p2);
    }

    private static Person createTempPersonFrom(Person from) {
        Person temp = new Person();
        temp.name = from.name;
        temp.age = from.age;
        return temp;
    }

    private static void copyIdentityDataFromTo(Person from, Person to) {
        to.name = from.name;
        to.age = from.age;
    }
}