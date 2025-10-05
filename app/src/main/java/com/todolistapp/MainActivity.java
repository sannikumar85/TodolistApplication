package com.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoAdapter.OnItemClickListener {
    
    private RecyclerView recyclerViewTodos;
    private TodoAdapter todoAdapter;
    private List<TodoItem> todoList;
    private List<TodoItem> filteredTodoList;
    private FloatingActionButton fabAddTodo;
    private BottomNavigationView bottomNavigation;
    private int nextTaskId = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        setupRecyclerView();
        setupBottomNavigation();
        setupFabClickListener();
    }
    
    private void initializeViews() {
        try {
            // Setup toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Todo List");
                }
            }
            
            recyclerViewTodos = findViewById(R.id.recycler_view_todos);
            fabAddTodo = findViewById(R.id.fab_add_todo);
            bottomNavigation = findViewById(R.id.bottom_navigation);
            
            // Initialize todo list
            todoList = new ArrayList<>();
            filteredTodoList = new ArrayList<>();
            
            // Add some sample todos
            todoList.add(new TodoItem(nextTaskId++, "Welcome to your Todo List! 📝"));
            todoList.add(new TodoItem(nextTaskId++, "Tap the + button to add new tasks"));
            todoList.add(new TodoItem(nextTaskId++, "Tap on tasks to mark them as complete"));
            todoList.add(new TodoItem(nextTaskId++, "Explore Categories and Statistics! 📊"));
            
            // Initialize filtered list with all todos
            filteredTodoList.addAll(todoList);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error initializing app: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    private void setupRecyclerView() {
        try {
            if (recyclerViewTodos != null) {
                recyclerViewTodos.setLayoutManager(new LinearLayoutManager(this));
                todoAdapter = new TodoAdapter(filteredTodoList, this);
                recyclerViewTodos.setAdapter(todoAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error setting up list: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    private void setupBottomNavigation() {
        try {
            if (bottomNavigation != null) {
                bottomNavigation.setOnItemSelectedListener(item -> {
                    int itemId = item.getItemId();
                    
                    if (itemId == R.id.nav_home) {
                        // Already on home
                        return true;
                    } else if (itemId == R.id.nav_profile) {
                        startActivity(new Intent(this, ProfileActivity.class));
                        return true;
                    }
                    
                    return false;
                });
                
                // Set home as selected
                bottomNavigation.setSelectedItemId(R.id.nav_home);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error setting up navigation: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    private void setupFabClickListener() {
        fabAddTodo.setOnClickListener(v -> showAddTaskDialog());
    }
    
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Task");
        
        final EditText input = new EditText(this);
        input.setHint("Enter task description...");
        input.setPadding(50, 30, 50, 30);
        builder.setView(input);
        
        builder.setPositiveButton("Add", (dialog, which) -> {
            String taskText = input.getText().toString().trim();
            if (!TextUtils.isEmpty(taskText)) {
                TodoItem newTask = new TodoItem(nextTaskId++, taskText);
                todoList.add(0, newTask); // Add to top of main list
                filteredTodoList.add(0, newTask); // Add to filtered list as well
                todoAdapter.notifyItemInserted(0);
                recyclerViewTodos.scrollToPosition(0);
                Toast.makeText(this, "Task added! ✅", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a task!", Toast.LENGTH_SHORT).show();
            }
        });
        
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    
    @Override
    public void onItemClick(int position) {
        TodoItem item = filteredTodoList.get(position);
        item.toggleCompletion();
        todoAdapter.notifyItemChanged(position);
        
        String message = item.isCompleted() ? "Task completed! 🎉" : "Task marked as incomplete";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int position) {
        showDeleteConfirmationDialog(position);
    }    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task");
        builder.setMessage("Are you sure you want to delete this task?");
        
        builder.setPositiveButton("Delete", (dialog, which) -> {
            TodoItem itemToDelete = filteredTodoList.get(position);
            todoList.remove(itemToDelete); // Remove from main list
            filteredTodoList.remove(position); // Remove from filtered list
            todoAdapter.notifyItemRemoved(position);
            Toast.makeText(this, "Task deleted! 🗑️", Toast.LENGTH_SHORT).show();
        });
        
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int itemId = item.getItemId();
            
            if (itemId == R.id.action_search) {
                // Handle search - SearchView will be automatically handled
                return true;
            } else if (itemId == R.id.action_statistics) {
                startActivity(new Intent(this, StatisticsActivity.class));
                return true;
            } else if (itemId == R.id.action_categories) {
                startActivity(new Intent(this, CategoriesActivity.class));
                return true;
            } else if (itemId == R.id.action_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            } else if (itemId == R.id.action_about) {
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error opening menu item", Toast.LENGTH_SHORT).show();
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            
            // Setup search functionality
            MenuItem searchItem = menu.findItem(R.id.action_search);
            if (searchItem != null) {
                SearchView searchView = (SearchView) searchItem.getActionView();
                if (searchView != null) {
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            filterTodos(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            filterTodos(newText);
                            return false;
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // If menu creation fails, at least the app won't crash
        }

        return true;
    }

    private void filterTodos(String query) {
        filteredTodoList.clear();
        
        if (TextUtils.isEmpty(query)) {
            filteredTodoList.addAll(todoList);
        } else {
            for (TodoItem todo : todoList) {
                if (todo.getText().toLowerCase().contains(query.toLowerCase())) {
                    filteredTodoList.add(todo);
                }
            }
        }
        
        todoAdapter.notifyDataSetChanged();
    }
}