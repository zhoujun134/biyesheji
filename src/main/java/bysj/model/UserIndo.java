package bysj.model;

/**
 * 用户信息
 */
public class UserIndo {
    private String name;//姓名
    private String role; //用户的角色
    private String password;//密码
    private int age;//年龄
    private char sex;//姓名
    private long phone;//电话
    private String address;//地址

    public UserIndo() {
    }

    public UserIndo(String name, String role, String password, int age, char sex, long phone, String address) {
        this.name = name;
        this.role = role;
        this.password = password;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserIndo{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                '}';
    }
}
