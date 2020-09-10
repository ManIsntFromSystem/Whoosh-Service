# Whoosh-Service
This app was made for the test task.</br>
At the SplashActivity just input api-key in the format("asdajkda.edfad3232.jkj2h3k232") - Like a true key:) </br>
App Stack - MVP, Moxy, Retrofit, Room, Dagger 2, Coruotine Flow.</br>
For Qr scan was used Zxing library. </br>
Also there are bottomNavigation, ViewPager2 and a few Material components. </br>

Now api-key is not available, but there is a possible to mock data during scan(just comment out/ upcomment a place of code in ScannerPresenter), </br>
For app working you just need to generete QR code with this url https://whoosh.app.link/scooter?scooter_code=P364 </br>
or put name into EditText(name = last 4 symbol in the ULR, case is not important).</br>

There is checking permission at the SplashActivity and the validation for api-key and QR-code.</br>
