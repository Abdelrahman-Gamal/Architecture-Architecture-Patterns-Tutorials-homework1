package com.alyndroid.architecturepatternstutorialshomework.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.alyndroid.architecturepatternstutorialshomework.R;
import com.alyndroid.architecturepatternstutorialshomework.databinding.ActivityMainBinding;
import com.alyndroid.architecturepatternstutorialshomework.db.DataBase;
import com.alyndroid.architecturepatternstutorialshomework.model.NumberModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NumberView {
    ActivityMainBinding activityMainBinding;
    NumberPresenter presenter;

    NumberViewModel numberViewModel ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        activityMainBinding.plusButton.setOnClickListener(this);
        activityMainBinding.divButton.setOnClickListener(this);

        presenter = new NumberPresenter(this);

        numberViewModel = ViewModelProviders.of(this).get(NumberViewModel.class);

        numberViewModel.NumberMutableLiveData.observe(this, i -> activityMainBinding.mulResultTextView.setText(i));
    }

    public int getPlusResult() {
        NumberModel currentNumberModel = new DataBase().getNumbers();
        return currentNumberModel.getFirstNum() + currentNumberModel.getSecondNum();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plus_button:
                String resultPlus = Integer.toString(getPlusResult());
                activityMainBinding.plusResultTextView.setText(resultPlus);
                break;
            case R.id.div_button:
                presenter.getdivResult();
                break;
            case R.id.mul_button:
                numberViewModel.getMulResult();
                break;

        }
    }


    @Override
    public void onGetNumber(int i) {
        activityMainBinding.divResultTextView.setText(Integer.toString(i));
    }
}
