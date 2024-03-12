import React from 'react';
import {View, Image} from 'react-native';

function AppIntroPicture() {
  return (
    <View style={{flex: 1, alignItems: 'center', marginTop: 80}}>
      <Image
        source={require('../images/startPage/appname.png')}
        style={{width: 280, height: 280}}></Image>
    </View>
  );
}

export default AppIntroPicture;
