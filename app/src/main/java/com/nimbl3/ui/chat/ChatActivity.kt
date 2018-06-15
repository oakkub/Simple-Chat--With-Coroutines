package com.nimbl3.ui.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nimbl3.R
import com.nimbl3.data.repository.Message
import com.nimbl3.lib.viewmodel.ViewModelFactory
import com.nimbl3.ui.base.BaseActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.item_view_chat.*
import javax.inject.Inject

class ChatActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ChatViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
    }

    private val adapter = ChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.setHasFixedSize(true)
        chatRecyclerView.adapter = adapter

        viewModel.messages.observe(this, Observer {
            if (it == null) {
                return@Observer
            }

            adapter.submitList(it)
        })

        sendButton.setOnClickListener {
            viewModel.send(chatEditText.text.toString())
        }
    }

    companion object {

        fun show(context: Context) {
            val intent = Intent(context, ChatActivity::class.java)
            context.startActivity(intent)
        }

    }

}

class ChatAdapter: ListAdapter<Message, ChatViewHolder>(messageDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatViewHolder.create(parent)

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) = holder.bind(getItem(position))

    companion object {

        private val messageDiffUtil = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }
        }

    }

}

class ChatViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(data: Message) {
        chatName.text = data.username
        chatMessage.text = data.message
    }

    companion object {

        fun create(parent: ViewGroup) : ChatViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_chat, parent, false)
            return ChatViewHolder(view)
        }

    }

}