package com.example.labs.fragments.Update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.labs.R
import com.example.labs.data.entities.Note
import com.example.labs.data.vm.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpdateFragment : Fragment() {
    private  val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mNoteViewModel: NoteViewModel
    private var selectedDate: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        view.findViewById<TextView>(R.id.updateNote).text = args.currentNote.note

        val updateButton = view.findViewById<Button>(R.id.update)
        updateButton.setOnClickListener {
            updateNote()
        }

        val deleteButton = view.findViewById<Button>(R.id.delete)
        deleteButton.setOnClickListener {
            deleteNote()
        }

        val backButton = view.findViewById<Button>(R.id.backToListFromUpdate)
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        val selectDateButton = view.findViewById<Button>(R.id.selectDate2)
        selectDateButton.setOnClickListener {
            showDatePickerDialog()
        }


        return  view
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

    private fun updateNote() {
        val noteText = view?.findViewById<EditText>(R.id.updateNote)?.text.toString()

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
            // Dates are not the same
            val formattedDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)

            val note = Note(args.currentNote.id, noteText, formattedDate)

            mNoteViewModel.updateNote(note)

            Toast.makeText(requireContext(), "Nota atualizada com sucesso!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
    }
    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Sim") { _, _ ->
            mNoteViewModel.deleteNote(args.currentNote)
            makeText(
                requireContext(),
                "Nota apagada com sucesso!",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Não") { _, _ -> }
        builder.setTitle("Apagar")
        builder.setMessage("Tem a certeza que pretende apagar a Nota?")
        builder.create().show()
    }
}