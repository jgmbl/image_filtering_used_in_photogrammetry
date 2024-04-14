# Image Filtering Used in Photogrammetry

Photogrammetry is a science field that deals with the creation of point clouds and 3D models. The project is an extension of my engineering thesis project.

## About the project
The goal of this project is to make the process of image filtering more accessible, since improves the quality of the point cloud. The type of image filtering depends on the quality of the images and the scenery. Note that image filtering requires more disk space and significantly increases project execution time. Image filtering is recommended for projects with a relatively small number of images.

## Project overview
The user can only import JPG/JPEG images due to the fact that photogrammetric software does not any import other image format. Gaussian blur, median blur and image sharpening are available. In addition, the user can set the blur value according to project requirements. The sharpening properties are set automatically, according to the most recent research publications. 

## Functionalities
Functionalities of the application include:
- image import
- deleting images from import
- applying filters with a user-given settings
- comparing the original and filtered photo
- exporting photos

## Technologies
Technologies used:
- JavaFX
-  OpenCV
- JUnit

## Tests
The code is tested manually and by unit tests.

## How to build and run
Open folder with *pom.xml* in terminal:
```
cd path-to-file/image_filtering/
```
 Type:
```
mvn clean javafx:run
```