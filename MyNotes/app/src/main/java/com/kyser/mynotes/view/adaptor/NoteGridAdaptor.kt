import com.kyser.mynotes.databinding.NoteGridItemBinding

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kyser.mynotes.model.Note

class NoteGridAdaptor(val mContext: Context, val itemEvent: ItemEvent )  :
    RecyclerView.Adapter<NoteGridAdaptor.Holder>() {

     private var noteList =  ArrayList<Note>()

    interface ItemEvent {
        fun onItemClick(itemView: View, note: Note)
        fun onItemMenuClick(itemView: View, note: Note)
    }


    fun setNoteList(noteList: List<Note>) {
        this.noteList.clear()
        this.noteList.addAll(noteList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            NoteGridItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemBinding.itemTitle.text = noteList[position].getName()
        holder.itemBinding.itemBody.text = noteList[position].getBody()
        holder.itemBinding.root.setOnClickListener { view -> itemEvent.onItemClick(view, noteList[position]) }
        holder.itemBinding.itemMenu.setOnClickListener { view ->
            itemEvent.onItemMenuClick( view, noteList[position])
        }
        val c = mContext?.resources.getIdentifier( "note_priority_" + noteList[position].getPriority(),"color", mContext?.packageName )
        val background: Drawable = holder.itemBinding.root.background
        if (background is ShapeDrawable) {
            background.paint.color = c
        } else if (background is GradientDrawable) {
            background.setColor(mContext!!.resources.getColor(c, mContext.theme))
        }
    }

    override fun getItemCount(): Int {
        return noteList?.size ?: 0
    }

    class Holder(val itemBinding: NoteGridItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
}