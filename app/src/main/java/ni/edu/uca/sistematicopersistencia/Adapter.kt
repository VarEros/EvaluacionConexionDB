package ni.edu.uca.sistematicopersistencia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.sistematicopersistencia.data.database.entities.EntityProducto

class Adapter : ListAdapter<EntityProducto, Adapter.ProdViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdViewHolder {
        return ProdViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProdViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.nombre)
    }

    class ProdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val prodItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            prodItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ProdViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_prod, parent, false)
                return ProdViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<EntityProducto>() {
            override fun areItemsTheSame(oldItem: EntityProducto, newItem: EntityProducto): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: EntityProducto, newItem: EntityProducto): Boolean {
                return oldItem.nombre == newItem.nombre
            }
        }
    }
}