package kz.studentlife.studenlifepilotapp.TimeTable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.studentlife.studenlifepilotapp.R;

public class TimeTableAdapterTeacher extends RecyclerView.Adapter<TimeTableAdapterTeacher.ViewHolder> {
    private List<TimeTableModel> lessonList;

    public TimeTableAdapterTeacher(List<TimeTableModel>lessonList){
        this.lessonList = lessonList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_rv_teacher, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String lessonName = lessonList.get(position).getLessonName();
        String lessonTime = lessonList.get(position).getLessonTime();
        holder.setData(lessonName,lessonTime);
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView lessonNameText;
        private TextView lessonTimeText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonNameText = itemView.findViewById(R.id.lessonName);
            lessonTimeText = itemView.findViewById(R.id.lessonTime);
        }

        public void setData(String lessonName, String lessonTime) {
            lessonNameText.setText(lessonName);
            lessonTimeText.setText(lessonTime);
        }
    }
}
