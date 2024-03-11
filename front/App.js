import {
  GluestackUIProvider,
  Text,
  Button,
  ButtonText,
} from '@gluestack-ui/themed';
import {config} from '@gluestack-ui/config';

export default function App() {
  return (
    <GluestackUIProvider config={config}>
      <Button>
        <ButtonText>Hello world</ButtonText>
      </Button>
      <Text>Hello World!</Text>
    </GluestackUIProvider>
  );
}
