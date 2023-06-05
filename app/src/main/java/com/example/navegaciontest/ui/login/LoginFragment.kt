package com.example.navegaciontest.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.example.navegaciontest.UserViewModel
import com.example.navegaciontest.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    companion object{
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }

    private lateinit var binding: FragmentLoginBinding

    //VIEWMODEL COMPARTIDO
    private val userViewModel: UserViewModel by activityViewModels()

    //VARIABLE BUNDLE PARA DEVOLVER UN VALOR
    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //SE OBTIENE EL BUNDLE DE LA PANTALLA ANTERIOR Y SE SETEA EN FALSE
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle.set(LOGIN_SUCCESSFUL, false)

        //SE LLAMA MÉTODO DEL LOGIN
        binding.btnLogin.setOnClickListener {
            login("Sergio", "123")
        }

    }


    fun login(username: String, password: String) {

        //SE INVOCA EL MÉTODO DEL VIEWMODEL QUE HACE EL LOGIN,
        //SE ACTUALIZA EL VALOR DEL LIVE DATA DESDE EL VIEWMODEL
        userViewModel.login(username, password).observe(viewLifecycleOwner, Observer { result ->
            if (result) {
                //SE SETEA EN EL BUNDLE QUE EL LOGIN FUE SUCCESS
                savedStateHandle.set(LOGIN_SUCCESSFUL, true)
                findNavController().popBackStack()
            } else {
                //SE MUESTRA UN ERROR AL USUARIO
                showErrorMessage()
            }
        })
    }

    fun showErrorMessage() {
        Toast.makeText( requireContext(), "USUARIO INCORRECTO",  Toast.LENGTH_LONG).show()
    }


}