package com.nikhilverma360.arclassroom;

public class MyClasses {
    private String classname,room,section,subject;

    public MyClasses(){
        // just needed to be empty
    }
    public MyClasses(String classname, String room, String section, String subject) {
        this.classname = classname;
        this.room = room;
        this.section = section;
        this.subject = subject;
    }

    public String getClassname() {
        return classname;
    }

    public String getRoom() {
        return room;
    }

    public String getSection() {
        return section;
    }

    public String getSubject() {
        return subject;
    }
}
