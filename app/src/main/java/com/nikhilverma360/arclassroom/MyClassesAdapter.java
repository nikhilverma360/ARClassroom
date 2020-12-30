package com.nikhilverma360.arclassroom;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class MyClassesAdapter extends FirestoreRecyclerAdapter<MyClasses, MyClassesAdapter.MyClassesHolder> {
    private OnItemClickListener listener;

    public MyClassesAdapter(@NonNull FirestoreRecyclerOptions<MyClasses> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull MyClassesHolder holder, int position, @NonNull MyClasses model) {
        holder.classroomname.setText(model.getClassname());
        holder.section.setText(model.getSection());
        holder.room.setText(model.getRoom());
        holder.subject.setText(model.getSubject());
    }
    @NonNull
    @Override
    public MyClassesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.classroom_cardview,
                parent, false);
        return new MyClassesHolder(v);
    }
    class MyClassesHolder extends RecyclerView.ViewHolder {
        TextView classroomname,section, room, subject;
        public MyClassesHolder(View itemView) {
            super(itemView);
            classroomname = itemView.findViewById(R.id.arclassname);
            section = itemView.findViewById(R.id.arclasssection);
            room= itemView.findViewById(R.id.arclassroomnum);
            subject= itemView.findViewById(R.id.arclasssubject);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
