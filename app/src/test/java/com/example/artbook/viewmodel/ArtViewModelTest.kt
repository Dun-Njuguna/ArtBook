package com.example.artbook.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.artbook.repo.FakeArtRepository
import com.example.artbook.util.MainCoroutineRule
import com.example.artbook.util.Status
import com.example.artbook.util.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup(){
        // Test Doubles
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `inserting art without year returns error`(){
        viewModel.makeArt("Test", "Testing", "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `inserting art without name returns error`(){
        viewModel.makeArt("", "Testing", "2000")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `inserting art without artistName returns error`(){
        viewModel.makeArt("Test", "", "2000")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

}