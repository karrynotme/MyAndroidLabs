package algonquin.cst2335.zhou0223.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;

import algonquin.cst2335.zhou0223.R;

public class MainActivity extends AppCompatActivity {
    ImageView imgView;
    Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 通过findViewById方法获取布局中的视图
        imgView = findViewById(R.id.imageView);
        sw = findViewById(R.id.spin_switch);
        sw.setOnCheckedChangeListener( (btn, isChecked) -> { {
            // 设置开关的状态变化监听器
            if (isChecked) {
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(5000);// 设置动画的持续时间为5秒
                rotate.setRepeatCount(Animation.INFINITE);// 设置动画的重复次数为无限
                rotate.setInterpolator(new LinearInterpolator());// 设置动画的插值器为线性插值器
                imgView.startAnimation(rotate);// 启动动画
            }else {   // 当开关状态为选中时，创建一个旋转动画对象
                // 当开关状态不选中时，清除视图的动画
                imgView.clearAnimation();
            }
        }
        });
    }

}