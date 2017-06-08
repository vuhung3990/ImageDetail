
Simple demo for show image detail
-------------
![demo 1](https://github.com/vuhung3990/ImageDetail/raw/master/1.png)
![demo 2](https://github.com/vuhung3990/ImageDetail/raw/master/2.png)

[video demo](https://www.youtube.com/watch?v=faA-wHg3poM)

**usage:**

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