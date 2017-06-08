# Simple image detail

# Features
  - swipe left|right to change image 
  - swipe up|down to dismiss
 
# demo 
![demo 1]("https://github.com/vuhung3990/ImageDetail/raw/master/Peek%202017-06-08%2010-48.gif")
# usage
```
ArrayList<String> imageUrls = new ArrayList<>();
imageUrls.add("http://i1.jpg");
imageUrls.add("http://i2.jpg");
imageUrls.add("http://i3.jpg");
imageUrls.add("http://i4.jpg");
imageUrls.add("http://i5.jpg");
Intent intent = new Intent(MainActivity.this, DetailActivity.class);
intent.putExtra(DetailInput.INPUT_KEY, new DetailInput.Builder()
        .commentCount(9)
        .likeCount(21)
        .listImageUrl(imageUrls)
        .title("Peter Tũn")
        .status("enjoy meme")
        .time("2 day ago")
        .build());
startActivity(intent);
```