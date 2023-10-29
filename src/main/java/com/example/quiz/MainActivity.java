package com.example.quiz;

import static com.example.quiz.PromptActivity.KEY_EXTRA_ANSWER_SHOWN;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String QUIZ_TAG = "MainActivity";
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button hintButton;
    private TextView questiontextView;
    private TextView sumview;
    private int score =0;
    private boolean this_questrion=false;

    private boolean answerWasShown;
    private Question[] questions=new Question[]
            {
                    new Question(R.string.q_activity,true),
                    new Question(R.string.q_version, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_find_resources, false)
};
    private int currentIndex = 0;
    public static final String KEY_EXTRA_ANSWER="com.example.quiz.correctAnswer";
    private static final String KEY_CURRENT_INDEX="currentIndex";
    private static final int REQUEST_CODE_PROMPT=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG,"wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);


        trueButton =findViewById(R.id.true_button);
        falseButton =findViewById(R.id.false_button);
        nextButton =findViewById(R.id.next_button);
        questiontextView = findViewById(R.id.question_text_view);
        sumview=findViewById(R.id.sum);
        hintButton=findViewById(R.id.hint_button);
        if(savedInstanceState !=null)
        {
            currentIndex=savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        setNextQuestion();


        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % questions.length;
                answerWasShown=false;
                setNextQuestion();
                sumview.setText(getString(R.string.sum)+" "+score);
            }
        });
        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent =new Intent(MainActivity.this,PromptActivity.class);
            boolean correctAnswer=questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER,correctAnswer);
                startActivityForResult(intent,REQUEST_CODE_PROMPT);
            //startActivity(intent);
            }
        });
    setNextQuestion();

    }

    private void checkAnswerCorrectness(boolean userAnswer)
    {
        boolean correctAnswer=questions[currentIndex].isTrueAnswer();
        int resultMessageId =0;
        if(answerWasShown) {
            resultMessageId=R.string.answer_was_shown;

        }
        else
        {
            if (userAnswer == correctAnswer)
            {
                resultMessageId = R.string.correct_answer;
                this_questrion = true;

            }
            else
            {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }
    private void setNextQuestion()
    {
        if(this_questrion)
        {
            score++;
            this_questrion=false;
        }
        questiontextView.setText(questions[currentIndex].getQuestionId());
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(QUIZ_TAG,"Wywołanie onStart");
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(QUIZ_TAG, "Wywołanie onResume");
    }
    @Override
    protected  void onPause()
    {
        super.onPause();
        Log.d(QUIZ_TAG, "Wywołanie onPause");
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(QUIZ_TAG,"Wywołanie onStop");

    }
    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d(QUIZ_TAG,"Wywołanie onRestart");
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(QUIZ_TAG,"Wywołanie onDestroy");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {

        super .onSaveInstanceState(outState);
        Log.d(QUIZ_TAG,"Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX,currentIndex);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode !=RESULT_OK){return;}
        if(requestCode ==REQUEST_CODE_PROMPT)
        {
            if(data == null){return;}
            answerWasShown= data.getBooleanExtra(KEY_EXTRA_ANSWER_SHOWN,false);
        }
    }


}
