package ex.com.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import ex.com.bakingapp.R;
import ex.com.bakingapp.data.db.Step;
import ex.com.bakingapp.databinding.StepItemBinding;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    private List<? extends Step> steps;
    @Nullable
    private final StepClickCallback stepClickCallback;
    public StepsAdapter(@Nullable StepClickCallback clickCallback) {
        stepClickCallback = clickCallback;
    }

    public void setStepList(final List<? extends Step> stepList) {
        if (steps == null) {
            steps = stepList;
            notifyItemRangeInserted(0, stepList.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() { return steps.size(); }
                @Override
                public int getNewListSize() { return stepList.size(); }
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Step newStep = stepList.get(newItemPosition);
                    Step oldStep = steps.get(oldItemPosition);
                    return oldStep.getId() == newStep.getId();
                }
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Step newStep = stepList.get(newItemPosition);
                    Step oldStep = steps.get(oldItemPosition);
                    return oldStep.getId() == newStep.getId()
                            && oldStep.getRecipeId() == newStep.getRecipeId()
                            && Objects.equals(oldStep.getShortDescription(), newStep.getShortDescription())
                            && Objects.equals(oldStep.getDescription(), newStep.getDescription())
                            && Objects.equals(oldStep.getVideoURL(), newStep.getVideoURL());
                }
            });
            steps = stepList;
            diffResult.dispatchUpdatesTo(this);
        }
    }
    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StepItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.step_item,
                        parent, false);
        binding.setCallback(stepClickCallback);
        return new StepViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.binding.setStep(steps.get(position));
        holder.binding.executePendingBindings();
    }
    @Override
    public int getItemCount() { return steps == null ? 0 : steps.size(); }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        final StepItemBinding binding;
        StepViewHolder(StepItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
