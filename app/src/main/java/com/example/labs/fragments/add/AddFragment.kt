package com.example.labs.fragments.add

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.labs.R
import com.example.labs.data.entities.Note
import com.example.labs.data.vm.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddFragment : Fragment() {
    private lateinit var mNoteViewModel: NoteViewModel
    private var selectedDate: Calendar = Calendar.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank, container, false)

        ViewModelProvider(this)[NoteViewModel::class.java].also { this.mNoteViewModel = it }

        val button = view.findViewById<Button>(R.id.save)
        button.setOnClickListener {
            addNote()
        }

        val backButton = view.findViewById<Button>(R.id.backToList)
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        val selectDateButton = view.findViewById<Button>(R.id.selectDate)
        selectDateButton.setOnClickListener {
            showDatePickerDialog()
        }

        return view
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun addNote() {
        val noteText = view?.findViewById<EditText>(R.id.addNote)?.text.toString()
        if (noteText.isEmpty() || noteText.length < 5) {
            Toast.makeText(requireContext(), "A nota deve ter pelo menos 5 caracteres.", Toast.LENGTH_LONG).show()
            return
        }
        if (selectedDate == null) {
            Toast.makeText(requireContext(), "A data não pode ser nula", Toast.LENGTH_LONG).show()
            return
        }

        val currentDate = Calendar.getInstance()

        if (currentDate.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR) &&
            currentDate.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH) &&
            currentDate.get(Calendar.DAY_OF_MONTH) == selectedDate.get(Calendar.DAY_OF_MONTH)
        ) {
            // Dates are the same
            Toast.makeText(requireContext(), "A data não pode ser nula", Toast.LENGTH_LONG).show()
        } else {
            val formattedDate =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)


            val note = Note(0, noteText, formattedDate)
            mNoteViewModel.addNote(note)

            Toast.makeText(requireContext(), "Nota adicionada com sucesso!", Toast.LENGTH_LONG)
                .show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }
}