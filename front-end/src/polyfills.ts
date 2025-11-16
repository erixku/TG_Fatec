import 'react-native-get-random-values';
import { decode as atob, encode as btoa } from 'base-64';
if (typeof global.atob === 'undefined') (global as any).atob = atob;
if (typeof global.btoa === 'undefined') (global as any).btoa = btoa;
import { Buffer } from 'buffer';
if (typeof (global as any).Buffer === 'undefined') (global as any).Buffer = Buffer;

// Export a lazy initializer so Metro won't attempt to require native modules
// during static analysis. Call `initBlobPolyfills()` from runtime (e.g.
// inside a `useEffect` in the app entry) so native modules are only
// required when running on a device/dev-client.
export async function initBlobPolyfills(): Promise<void> {
  try {
    // eslint-disable-next-line @typescript-eslint/no-var-requires
    const RNFetchBlob = require('react-native-blob-util');
    if (RNFetchBlob && RNFetchBlob.polyfill) {
      try {
        (global as any).XMLHttpRequest = RNFetchBlob.polyfill.XMLHttpRequest;
        (global as any).Blob = RNFetchBlob.polyfill.Blob;
        (global as any).FileReader = RNFetchBlob.polyfill.FileReader;
      } catch (e) {
        // eslint-disable-next-line no-console
        console.warn('RNFetchBlob polyfill initialization failed:', e);
      }
    }
  } catch (e) {
    // Quietly ignore: most likely running under Metro/node where native
    // module isn't available.
    // eslint-disable-next-line no-console
    console.info('react-native-blob-util not available at runtime; skipping polyfills.');
  }
}

export {};
