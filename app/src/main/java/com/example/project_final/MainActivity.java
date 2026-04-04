package com.example.project_final;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DeadlineAdapter adapter;
    List<Deadline> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Step 1: Connect RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // Step 2: Create List
        list = new ArrayList<>();

        // Step 3: Add Dummy Data
        list.add(new Deadline("Math Assignment", "Mathematics", "2026-04-05", "auto"));
        list.add(new Deadline("Physics Lab", "Physics", "2026-04-03", "auto"));
        list.add(new Deadline("DSA Project", "Computer Science", "2026-04-10", "auto"));

        // Step 4: Create Adapter
        adapter = new DeadlineAdapter(list);

        // Step 5: Set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Step 6: Set Adapter
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAdd);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddDeadlineActivity.class);
            startActivity(intent);
        });
    }
}
