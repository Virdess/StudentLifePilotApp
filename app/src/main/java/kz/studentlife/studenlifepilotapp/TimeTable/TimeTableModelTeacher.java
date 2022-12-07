package kz.studentlife.studenlifepilotapp.TimeTable;

public class TimeTableModelTeacher {
    private String lessonName;
    private String lessonTime;

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(String lessonTime) {
        this.lessonTime = lessonTime;
    }

    public TimeTableModelTeacher(String lessonName, String lessonTime) {
        this.lessonName = lessonName;
        this.lessonTime = lessonTime;
    }
}
