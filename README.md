# ipfs-privatebin
IPBin is a pastebin like private service which let's you upload AES-encrypted files to the IPFS network. It is free, anonymous and open source
## How do I use IPBin?
1. Download IPBin.jar [here](https://github.com/BowStar/ipfs-privatebin/blob/master/jars/IPBin.jar?raw=true).
2. Run your IPFS node. You can find the tutorial [here](https://ipfs.io/docs/install/)
3. Run it with `java -jar IPBin.jar`
4. Now, IPBin.jar runs! There are two commands:

`upload [dir/to/file/]`: Upload the file to IPFS. After you entered this command, the programm will output a key and a hash. The hash is the hash of the encrypted file in the IPFS network and the key is the key to decrypt your file.

`open [hash]`: Open an encrypted file. Use the hash and the key to open the file. The programm will output the content of your file in the terminal.
## How do I support this project?
This project is open source. So feel free to use it, download it and improve it. I would be very greatful if you want to support this project financially:

BTC: `1FxX9YtMdvdWFiigRFHZ3CqdD3GqU7C14i`

ETH: `0xb2ffe08eddfd9932a7a8c3c8f8c7695f8be35dad`
