package at.xa1.saveto

import android.content.Intent
import at.xa1.saveto.model.intentToSource
import at.xa1.saveto.navigation.Coordinator
import at.xa1.saveto.navigation.Destination
import at.xa1.saveto.ui.SaveArgs
import at.xa1.saveto.ui.SplashDestination

class MainCoodrdinator(
    private val saveFlow: Destination<SaveArgs>,
) : Coordinator<MainArgs>() {

    override fun onStart() {
        super.onStart()

        when (args.intent.action) {
            Intent.ACTION_MAIN -> explicitStart()
            Intent.ACTION_SEND, Intent.ACTION_VIEW -> saveFlow()
            else -> explicitStart() // TODO report somehow?
        }
    }

    private fun explicitStart() {
        navigator.goTo(SplashDestination)
    }

    private fun saveFlow() {
        navigator.goTo(saveFlow) {
            SaveArgs(
                source = intentToSource(args.intent),
                onClose = args.onClose
            )
        }
    }
}

data class MainArgs(
    val intent: Intent,
    val onClose: (result: MainResult) -> Unit
)

enum class MainResult {
    OK,
    ABORT
}
