package com.example.samrans.noteapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.samrans.noteapp.R;
import com.example.samrans.noteapp.models.Notes;
import com.example.samrans.noteapp.utils.ClickListen;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Samrans on 16-10-2017.
 */


public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Typeface font;
    private final String today;
    Context mContext;
    ArrayList<Notes> notesArrayList;
    ClickListen clickListen;

    public NotesAdapter(Context mContext, ArrayList<Notes> notesArrayList, ClickListen clickListen) {
        this.mContext = mContext;
        this.notesArrayList = notesArrayList;
        font = Typeface.createFromAsset(mContext.getAssets(), "fonts/opensansbold.ttf");
        Calendar calendar = Calendar.getInstance();
        this.clickListen = clickListen;
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Notes.TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                return new NoteHolderText(view);
            case Notes.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                return new NoteHolderCheckBox(view);
            case Notes.CHECKBOX_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_type, parent, false);
                return new NoteHolderCheckBox(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoteHolderText) {
            NoteHolderText noteHolderText = (NoteHolderText) holder;
            noteHolderText.edt_header.setText(notesArrayList.get(position).getHeaderNote());
            noteHolderText.edt_note_detail.setText(notesArrayList.get(position).getDetails());
            noteHolderText.edt_header.setTypeface(font);

            noteHolderText.edt_header.setFocusable(false);
            noteHolderText.edt_header.setFocusableInTouchMode(false);
            noteHolderText.edt_header.setClickable(false);

            noteHolderText.edt_note_detail.setFocusable(false);
            noteHolderText.edt_note_detail.setFocusableInTouchMode(false);
            noteHolderText.edt_note_detail.setClickable(false);
            int val=notesArrayList.get(position).getNoteColor();
            if(val!=-1)
            noteHolderText.cardColor.setCardBackgroundColor(val);

        }
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        switch (notesArrayList.get(position).getType()) {
            case 0:
                return Notes.TEXT_TYPE;
            case 1:
                return Notes.CHECKBOX_TYPE;
            case 2:
                return Notes.IMAGE_TYPE;
            default:
                return -1;
        }
    }


    public class NoteHolderCheckBox extends RecyclerView.ViewHolder {
        public NoteHolderCheckBox(View itemView) {
            super(itemView);
        }
    }

    public class NoteHolderText extends RecyclerView.ViewHolder {
        EditText edt_header;
        EditText edt_note_detail;
        CardView  cardColor;
;
        public NoteHolderText(View itemView) {
            super(itemView);
            edt_note_detail = (EditText) itemView.findViewById(R.id.edt_note_detail);
            edt_header = (EditText) itemView.findViewById(R.id.edt_header);
            cardColor = (CardView) itemView.findViewById(R.id.cardColor);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListen.click(getAdapterPosition(),notesArrayList.get(getAdapterPosition()));
                }
            });
        }


    }
}
