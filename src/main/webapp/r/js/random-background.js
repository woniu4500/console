$().ready(function() {
  var random = $('body').hasClass('random-background');
  
  var imgs = new Array();
  imgs[0] = 'r/img/background/9b8b99ae-44d9-4db1-82fe-dab028dc730c.jpg';
  imgs[1] = 'r/img/background/99f56952-bef9-47f5-a513-f470911e7f49.jpg';
  imgs[2] = 'r/img/background/84701384-aeb5-4d57-b0bd-821bbd3627d6.jpg';
  imgs[3] = 'r/img/background/7e13fd5c-e8dc-47aa-925c-20a05c994cec.jpg';
  imgs[4] = 'r/img/background/3c9bcbc0-15dc-4a6f-a81f-5112936b7773.jpg';
  imgs[5] = 'r/img/background/5e52f416-c95f-4752-85a3-256576d53bdc.jpg';

  if (random) {
    index = _.random(0, imgs.length - 1);
    path = imgs[index];

    $('body').css('background-image', 'url(' + path + ')');
  };
});