setImageType('BRIGHTFIELD_H_E');
setColorDeconvolutionStains('{"Name" : "H&E default", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049 ", "Stain 2" : "Eosin", "Values 2" : "0.2159 0.8012 0.5581 ", "Background" : " 255 255 255 "}');
resetSelection();

// Find tissue
//createAnnotationsFromPixelClassifier("T", 50000.0, 0.0, "DELETE_EXISTING", "SELECT_NEW")
createAnnotationsFromPixelClassifier("T1", 500000.0, 1000000.0, "DELETE_EXISTING", "SELECT_NEW")

// Find tumors with px classifier
//createDetectionsFromPixelClassifier("Hema_Tumor", 500.0, 100.0, "SPLIT", "DELETE_EXISTING")
createDetectionsFromPixelClassifier("HT1", 5000.0, 10000.0, "SPLIT", "DELETE_EXISTING")

/*
// Find tumor areas within the tissue
createDetectionsFromPixelClassifier("TC", 500.0, 0.0, "SPLIT", "DELETE_EXISTING")

// Delete Stroma detections
selectObjectsByClassification("Stroma");
clearSelectedObjects(true);
*/

// Measure all tumor areas and shape characteristics
selectObjectsByClassification("Tumor");
addShapeMeasurements("AREA", "CIRCULARITY", "MAX_DIAMETER", "MIN_DIAMETER")

name = getProjectEntry().getImageName() + '.txt'
path = buildFilePath(PROJECT_BASE_DIR, 'annotation results')
mkdirs(path)
path = buildFilePath(path, name)
saveAnnotationMeasurements(path)

path = buildFilePath(PROJECT_BASE_DIR, 'detection results')
mkdirs(path)
path = buildFilePath(path, name)
saveDetectionMeasurements(path)

print 'Results exported to ' + path

resetSelection();

