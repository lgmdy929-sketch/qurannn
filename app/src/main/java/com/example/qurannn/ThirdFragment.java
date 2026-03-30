package com.example.qurannn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.qurannn.databinding.FragmentThirdBinding;

public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;

    private int startAyah = 1;
    private int endAyah = 5;
    private int repeatCount = 3;
    private int currentAyah;
    private int currentRepeat;
    private boolean isPlaying = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // استقبال البيانات من SecondFragment
        if (getArguments() != null) {
            startAyah = getArguments().getInt("startAyah", 1);
            endAyah = getArguments().getInt("endAyah", 5);
            repeatCount = getArguments().getInt("repeat", 3);
        }

        currentAyah = startAyah;
        currentRepeat = 0;
        updateUI();

        // زر تشغيل/إيقاف مؤقت
        binding.buttonPlayPause.setOnClickListener(v -> {
            isPlaying = !isPlaying;
            if (isPlaying) {
                binding.buttonPlayPause.setText("إيقاف مؤقت");
                Toast.makeText(getContext(),
                        "جاري تشغيل الآية " + currentAyah,
                        Toast.LENGTH_SHORT).show();
            } else {
                binding.buttonPlayPause.setText("تشغيل");
                Toast.makeText(getContext(),
                        "تم الإيقاف المؤقت",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // زر الآية التالية
        binding.buttonNext.setOnClickListener(v -> {
            currentRepeat++;
            if (currentRepeat >= repeatCount) {
                currentRepeat = 0;
                if (currentAyah < endAyah) {
                    currentAyah++;
                    Toast.makeText(getContext(),
                            "انتقال للآية " + currentAyah,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),
                            "انتهى نطاق الحفظ!",
                            Toast.LENGTH_LONG).show();
                }
            }
            updateUI();
        });

        // زر الآية السابقة
        binding.buttonPrevious.setOnClickListener(v -> {
            if (currentAyah > startAyah) {
                currentAyah--;
                currentRepeat = 0;
                Toast.makeText(getContext(),
                        "رجوع للآية " + currentAyah,
                        Toast.LENGTH_SHORT).show();
                updateUI();
            } else {
                Toast.makeText(getContext(),
                        "هذه أول آية في النطاق",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // زر إيقاف
        binding.buttonStop.setOnClickListener(v -> {
            isPlaying = false;
            currentAyah = startAyah;
            currentRepeat = 0;
            binding.buttonPlayPause.setText("تشغيل");
            Toast.makeText(getContext(),
                    "تم الإيقاف والرجوع للبداية",
                    Toast.LENGTH_SHORT).show();
            updateUI();
        });

        // زر الرئيسية
        binding.buttonHome.setOnClickListener(v ->
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_thirdFragment_to_firstFragment)
        );
    }

    private void updateUI() {
        binding.textViewAyahNumber.setText("الآية: " + currentAyah);
        binding.textViewRepeatCount.setText("التكرار: " + currentRepeat + "/" + repeatCount);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}