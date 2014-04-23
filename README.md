# tabs-in-live

## Android app

Android application to display tabs/music sheet on tablet, and scroll it with feet (when playing bass, it's hard to scroll with fingers).
I didn't find a mouse usable with feet, so I used a regular mouse with a box around to allow me to only click on the left click. If someone knows a better solution, please tell me :)


This is the very beginning, so a lot of things are static, and I only test it on my own tablet (archos titanium).

Import tabs
-----------

For now, there is not recording of tabs in the app. File are taken from the sdcard.
On the first screen, you have to import tabs and wait for the "import is done" toast.

The app manage 2 level of folder:
* concert 1
	* song 1
		* file1.png
		* file2.png
	* song 2
		* file1.png
		* file2.png

File are displayed in the alphabetic order, so if you want to order, you can prefix it with a digit.

To import tabs, the path must be: **/sdcard/tabsinlive/**concertName/tabName/1 sheet.png

Display tabs
------------

After importing you png files, you can open your tabs. On the top of the screen, there are 2 list box : one to select the concert, and the second to select the tab.

## Installation

I will soon move to gradle, but for now I'm using maven.

1. Go to TabsinliveProject/Tabsinlive where the pom.xml is
2. Make ** mvn clean install **
3. Connect you favorite Android device, OS > 4.0
4. Launch ** mvn android:redeploy android:run **