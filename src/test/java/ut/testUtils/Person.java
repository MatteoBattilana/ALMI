package ut.testUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable {
    private String       mName;
    private int          mAge;
    private List<Person> mChildren;

    public Person(String name, int age) {
        mName = name;
        mAge = age;
        mChildren = new ArrayList<>();
    }

    public boolean addChild(Person child)
    {
        return mChildren.add(child);
    }

    public String getName() {
        return mName;
    }

    public int getAge() {
        return mAge;
    }

    public List<Person> getChildren()
    {
        return mChildren;
    }

    @Override
    public int hashCode() {
        return String.format("%s-%s", mName, mAge).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person that = (Person) o;
        return mName.equals(that.getName())
                && mAge == that.getAge()
                && mChildren.equals(that.getChildren());
    }
}
