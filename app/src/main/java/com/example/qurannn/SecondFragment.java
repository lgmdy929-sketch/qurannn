package com.example.qurannn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.qurannn.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.numberPickerRepeat.setMinValue(1);
        binding.numberPickerRepeat.setMaxValue(20);
        binding.numberPickerRepeat.setValue(3);

        // زر ابدأ الحفظ - ينتقل لشاشة التكرار
        binding.buttonConfirm.setOnClickListener(v -> {
            String startStr = binding.editTextStartAyah.getText().toString().trim();
            String endStr = binding.editTextEndAyah.getText().toString().trim();
            int repeat = binding.numberPickerRepeat.getValue();

            if (startStr.isEmpty() || endStr.isEmpty()) {
                Toast.makeText(getContext(),
                        "من فضلك أدخل رقم الآية الأولى والأخيرة",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            int startAyah = Integer.parseInt(startStr);
            int endAyah = Integer.parseInt(endStr);

            if (startAyah > endAyah) {
                Toast.makeText(getContext(),
                        "رقم الآية الأولى يجب أن يكون أصغر من الأخيرة",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // إرسال البيانات لشاشة التكرار
            Bundle bundle = new Bundle();
            bundle.putInt("startAyah", startAyah);
            bundle.putInt("endAyah", endAyah);
            bundle.putInt("repeat", repeat);

            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_secondFragment_to_thirdFragment, bundle);
        });

        // زر الرجوع
        binding.buttonBack.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_secondFragment_to_firstFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}