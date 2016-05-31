package us.gigigogreenlabs.passwordedittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import com.example.PassValidator;
import com.example.Validator;

/**
 * Created by josemoralejo on 19/5/15.
 */
public class ShowHidePasswordView  extends EditText {

    private static final int SHOW_TYPE_ON_CLICK = 0;
    private static final int SHOW_TYPE_LONG_PRESS = 1;
    private Validator passValidator;
    private int imgNotSecure = android.R.drawable.ic_partial_secure;
    private int imgSecure = android.R.drawable.ic_secure;

    private boolean clicable;
    private boolean visible;

    public ShowHidePasswordView(Context context) {
        this(context, null);
    }

    public ShowHidePasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent(attrs);
    }

    public ShowHidePasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent(attrs);
    }

    private void initComponent(AttributeSet attrs) {
        initView();
        loadAttributes(attrs);
        setListeners();
        performHidePassword();
        addTextChangedListener(textWatcher);
    }

    private void initView(){
        this.setInputType(EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS);//No suggestions.

    }

    public void setImageResourceForLock(int resIdUnlocked, int resIdLocked){
        imgNotSecure = resIdUnlocked;
        imgSecure = resIdLocked;
        performHidePassword();
    }

    private void loadAttributes(AttributeSet attrs) {
        if( attrs!= null) {
            // Procesamos los atributos XML personalizados
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ShowHidePasswordView);

            int showType = a.getInt(R.styleable.ShowHidePasswordView_show_type, SHOW_TYPE_LONG_PRESS);
            clicable = ( showType == SHOW_TYPE_ON_CLICK)? true: false;
            a.recycle();
        }
    }

    private void setListeners() {
        this.setOnTouchListener(onTouchListener);
    }

    OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if(clicable){
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getRawX() >= (ShowHidePasswordView.this.getRight() - ShowHidePasswordView.this.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (visible) {
                            performHidePassword();
                        } else {
                            performShowPassword();
                        }
                        visible = !visible;
                    }
                }

            } else {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (ShowHidePasswordView.this.getRight() - ShowHidePasswordView.this.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        performShowPassword();
                        return true;
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (event.getRawX() >= (ShowHidePasswordView.this.getRight() - ShowHidePasswordView.this.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        performHidePassword();
                        return true;
                    }
                }
            }
            return false;
        }

    };

    @TargetApi(Build.VERSION_CODES.CUPCAKE) private void performHidePassword() {
        this.setCompoundDrawablesWithIntrinsicBounds(0, 0, imgSecure, 0);
        this.setTransformationMethod(new PasswordTransformationMethod());
        this.setSelection(this.getText().length());
    }

    private void performShowPassword() {
        this.setCompoundDrawablesWithIntrinsicBounds(0, 0, imgNotSecure, 0);
        this.setTransformationMethod(null);
        this.setSelection(this.getText().length());
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override public void afterTextChanged(Editable s) {
            passValidator = new PassValidator(s.toString());
            if (!passValidator.validate()){
                Toast.makeText(getContext(), "Pass is Wrong", Toast.LENGTH_LONG).show();
            }
        }
    };

}
