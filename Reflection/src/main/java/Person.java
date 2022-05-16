public class Person {

    private Long Id;
    public String name;
    public String address;

    public void eat() {
        System.out.println(name + "eating...");
    }

    public void eat(String food) {
        System.out.println(name + " eat " + food);
    }

    public Person() {
    }

    public Person(Long id, String name, String address) {
        Id = id;
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
