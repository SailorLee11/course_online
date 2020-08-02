package com.course.server.dto;

/**
 * class:ChapterPageDto
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */
public class ChapterPageDto extends PageDto {
    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "ChapterPageDto{" +
                "courseId='" + courseId + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
