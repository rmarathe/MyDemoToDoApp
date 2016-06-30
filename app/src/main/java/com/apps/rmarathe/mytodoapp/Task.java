package com.apps.rmarathe.mytodoapp;

import android.graphics.Color;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Task implements Serializable {
    private String taskName;
    private String taskPriority;
    private String taskStatus;
    private String taskDueDate;

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    @Override
    public String toString(){
        return " [ " + this.taskPriority + " ] " + "\t\t" + this.taskName +"\t\t" + " [ " + this.taskStatus + " ] ";
    }

    public int getColorByStatus(){
        if(this.taskStatus.equals("Yet To Start")){
            return Color.rgb(204,0,0);//red
        }
        if(this.taskStatus.equals("Doing")){
            return Color.rgb(0,153,0); //green
        }
        if(this.taskStatus.equals("Done")){
            return Color.rgb(200,200,200); //gray
        }
        return Color.rgb(0,0,0); //black
    }

    public boolean isTaskStatusDone(String status){
        if(status.equals("Done")){
            return true;
        }
        return false;
    }

    public int getPrioritySelection(){
        int priority = 0;
        switch(this.taskPriority){
            case "Low":
                priority = 0;
                break;
            case "Medium":
                priority = 1;
                break;
            case "High":
                priority =2;
                break;
            default:
                priority=0;
                break;
        }
        return priority;
    }

    public int getStatusSelection(){
        int status = 0;
        switch(this.taskStatus){
            case "Yet To Start":
                status = 0;
                break;
            case "Doing":
                status = 1;
                break;
            case "Done":
                status =2;
                break;
            default:
                status=0;
                break;
        }
        return status;
    }


}
