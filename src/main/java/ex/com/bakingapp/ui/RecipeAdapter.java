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
import ex.com.bakingapp.data.db.Recipe;
import ex.com.bakingapp.databinding.ListItemBinding;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    List<? extends Recipe> recipes;
    @Nullable
    private final RecipeClickCallback recipeClickCallback;

    public RecipeAdapter(@Nullable RecipeClickCallback clickCallback) {
        recipeClickCallback = clickCallback;
    }

    public void setRecipeList(final List<? extends Recipe> recipeList) {
        if (recipes == null) {
            recipes = recipeList;
            notifyItemRangeInserted(0, recipeList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return recipes.size();
                }
                @Override
                public int getNewListSize() {
                    return recipeList.size();
                }
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return recipes.get(oldItemPosition).getId() ==
                            recipeList.get(newItemPosition).getId();
                }
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Recipe newRecipe = recipeList.get(newItemPosition);
                    Recipe oldRecipe = recipes.get(oldItemPosition);
                    return newRecipe.getId() == oldRecipe.getId()
                            && Objects.equals(newRecipe.getName(), oldRecipe.getName())
                            && newRecipe.getServings() == oldRecipe.getServings()
                            && Objects.equals(newRecipe.getImage(), oldRecipe.getImage());
                }
            });
            recipes = recipeList;
            result.dispatchUpdatesTo(this);
        }
    }
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item,
                        parent, false);
        binding.setCallback(recipeClickCallback);
        return new RecipeViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.binding.setRecipe(recipes.get(position));
        holder.binding.executePendingBindings();
    }
    @Override
    public int getItemCount() { return recipes == null ? 0 : recipes.size(); }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        final ListItemBinding binding;
        public RecipeViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
