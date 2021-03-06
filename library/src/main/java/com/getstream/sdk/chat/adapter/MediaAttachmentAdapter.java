package com.getstream.sdk.chat.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.getstream.sdk.chat.databinding.StreamItemSelectPhotoBinding;
import com.getstream.sdk.chat.model.AttachmentMetaData;
import com.getstream.sdk.chat.model.ModelType;
import com.getstream.sdk.chat.utils.Constant;
import com.getstream.sdk.chat.utils.StringUtility;

import java.io.File;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MediaAttachmentAdapter extends RecyclerView.Adapter<MediaAttachmentAdapter.MyViewHolder> {

    private final String TAG = MediaAttachmentAdapter.class.getSimpleName();
    private final OnItemClickListener listener;
    private Context context;
    private List<AttachmentMetaData> mediaPaths;
    public MediaAttachmentAdapter(Context context, List<AttachmentMetaData> mediaPaths, OnItemClickListener listener) {
        this.context = context;
        this.mediaPaths = mediaPaths;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        StreamItemSelectPhotoBinding itemBinding =
                StreamItemSelectPhotoBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.bind(mediaPaths.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mediaPaths.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final StreamItemSelectPhotoBinding binding;

        public MyViewHolder(StreamItemSelectPhotoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AttachmentMetaData attachment, final OnItemClickListener listener) {
            File file = new File(attachment.file.getPath());
            Uri imageUri = Uri.fromFile(file);
            Glide.with(context)
                    .load(imageUri)
                    .into(binding.ivMedia);

            if (attachment.isSelected)
                binding.ivSelectMark.setVisibility(View.VISIBLE);
            else
                binding.ivSelectMark.setVisibility(View.GONE);

            binding.ivLargeFileMark.setVisibility(file.length()> Constant.MAX_UPLOAD_FILE_SIZE ? View.VISIBLE : View.INVISIBLE);

            if (attachment.type.equals(ModelType.attach_file)) {
                binding.tvLength.setText(StringUtility.convertVideoLength(attachment.videoLength));
            } else {
                binding.tvLength.setText("");
            }
            itemView.setOnClickListener((View v) -> {
                listener.onItemClick(getAdapterPosition());
            });
            binding.executePendingBindings();
        }
    }
}
