package fun.oop.framework.util;

import org.junit.Test;
import fun.oop.framework.render.RenderJson;

import javax.xml.bind.annotation.*;
import java.util.List;




public class XmlUtilJavaTest {
    @Test
    public void testMarshal() {
        User user = (new User("张三", 11));
        System.out.println(RenderJson.success(user));


//        List<Friend> friends = Lists.newArrayList(new Friend("李四", 12), new Friend("王五", 14));
//        user.setFriends(friends);
//        System.out.println(XmlUtil.object2Xml(user));
        //User user = XmlUtil.xml2Object(User.class,XmlUtil.object2Xml(new User("张三", 11)));
        //System.out.println(user.getAge());
    }
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
class User {
    private String name;
    private Integer age;
    @XmlElementWrapper(name = "friends")
    @XmlElement(name = "friend")
    private List<Friend> friends;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public User() {
    }
}

class Friend {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Friend() {
    }

    public Friend(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}