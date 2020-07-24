/**
 * Script to export a rendered (RGB) image in QuPath v0.2.0.
 *
 * This is much easier if the image is currently open in the viewer,
 * then see https://qupath.readthedocs.io/en/latest/docs/advanced/exporting_images.html
 *
 * The purpose of this script is to support batch processing (Run -> Run for project (without save)),
 * while using the current viewer settings.
 *
 * Note: This was written for v0.2.0 only. The process may change in later versions.
 *
 * @author Pete Bankhead
 */

import qupath.imagej.tools.IJTools
import qupath.lib.gui.images.servers.RenderedImageServer
import qupath.lib.gui.viewer.overlays.HierarchyOverlay
import qupath.lib.regions.RegionRequest

import static qupath.lib.gui.scripting.QPEx.*

// It is important to define the downsample!
// This is required to determine annotation line thicknesses
double downsample = 10

// Add the output file path here
//String path = buildFilePath(PROJECT_BASE_DIR, 'rendered', getProjectEntry().getImageName() + '.png')
def name = getProjectEntry().getImageName()
def path = buildFilePath(PROJECT_BASE_DIR,'rgb overlays results', name.replaceAll('.svs','-withTumors.png'))

// Request the current viewer for settings, and current image (which may be used in batch processing)
def viewer = getCurrentViewer()
def imageData = getCurrentImageData()

// Tune options for opacity 30%
def options = viewer.getOverlayOptions()
options.setFillDetections(true)
options.setOpacity(0.5)

// Create a rendered server that includes a hierarchy overlay using the current display settings
server = new RenderedImageServer.Builder(imageData)
    .downsamples(downsample)
    .layers(new HierarchyOverlay(viewer.getImageRegionStore(), options, imageData))
    .build()

writeImage(server, path)
