package at.xa1.saveto.ui

import android.net.Uri
import at.xa1.saveto.android.SaveDialog
import at.xa1.saveto.android.StreamCopy
import at.xa1.saveto.model.getFilenameFrom
import at.xa1.saveto.navigation.Coordinator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaveCoordinator(
    private val scope: CoroutineScope,
    private val saveDialog: SaveDialog,
    private val streamCopy: StreamCopy
) : Coordinator<SaveArgs>() {
    override fun onStart() {
        super.onStart()
        navigator.goTo(SplashDestination)
        scope.launch { preview() }
    }

    private fun preview() {
        navigator.goTo(SourcePreviewDestination) {
            SourcePreviewArgs(
                source = args.source,
                onSave = ::save
            )
        }
    }

    private fun save() {
        navigator.goTo(LoadingDestination)
        scope.launch {
            val filename = getFilenameFrom(args.source.intent)
            val result = saveDialog.show(args.source.type, filename)

            if (result == null) {
                abort()
            } else {
                copy(result)
            }
        }
    }

    private fun abort() {
        args.onClose()
    }

    private fun copy(destinationUri: Uri) {
        scope.launch {
            withContext(Dispatchers.IO) {
                streamCopy.copy(args.source.sourceUri, destinationUri)
            }
            args.onClose()
        }
    }
}

data class SaveArgs(
    val source: Source,
    val onClose: () -> Unit
)
