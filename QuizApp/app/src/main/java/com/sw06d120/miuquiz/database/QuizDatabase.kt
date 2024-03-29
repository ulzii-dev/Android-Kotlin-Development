package com.sw06d120.miuquiz.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sw06d120.miuquiz.database.daos.AnswerDao
import com.sw06d120.miuquiz.database.daos.ChoiceDao
import com.sw06d120.miuquiz.database.daos.QuestionDao
import com.sw06d120.miuquiz.database.daos.QuizDao
import com.sw06d120.miuquiz.database.entities.Answer
import com.sw06d120.miuquiz.database.entities.Choice
import com.sw06d120.miuquiz.database.entities.Question
import com.sw06d120.miuquiz.database.entities.Quiz

@Database(
    entities = [
        Answer::class,
        Choice::class,
        Question::class,
        Quiz::class
    ], version = 5, exportSchema = false
)
abstract class QuizDatabase() : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun choiceDao(): ChoiceDao
    abstract fun answerDao(): AnswerDao
    abstract fun quizDao(): QuizDao

    companion object {
        @Volatile
        private var instance: QuizDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            QuizDatabase::class.java,
            "quiz_app.db"
        )
            .fallbackToDestructiveMigration()
            .addCallback(sRoomDatabaseCallback).build()

        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                instance?.let {
                    PopulateDbAsync(it).run {

                    }
                }
            }
        }
    }

    private class PopulateDbAsync internal constructor(db: QuizDatabase) :
        AsyncTask<Void?, Void?, Void?>() {
        private val qDao: QuizDao

        init {
            qDao = db.quizDao()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            qDao.deleteAll()
            return null
        }
    }
}
