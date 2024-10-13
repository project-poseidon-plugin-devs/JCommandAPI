# JCommandAPI
A lightweight Command API for Project Poseidon, based on [RedNapPanda's Command API.](https://github.com/RedNapPanda/CommandAPI)

Removes the need to register commands in plugin.yml, automatically checks for permission, argument count, if the command was sent by a player etc. and provides the ability to register subcommands.

## How to register Commands
```java
CommandManager manager = new CommandManager(this); // Plugin instance
manager.registerCommands(new CommandList(), new CommandVillage());
```
```java
public class CommandVillage {

    @CommandHandler(command = "village", aliases = {"v"}, description = "Village base command", usage = "/village",
                    permission = "permission.village", playersOnly = true, minArgs = 0, maxArgs = 0)
    public void onVillage(CommandInfo info) {
      // Village base command
    }

    @CommandHandler(command = "village.info")
    public void onVillageHelp(CommandInfo info) {
      // Village Help subcommand 
    }
}
```
## How to include Maven Dependency
In your *pom.xml*:
```
<repositories>
  <repository>
    <id>jcommandapi</id>
    <url>https://github.com/project-poseidon-plugin-devs/JCommandAPI/raw/refs/heads/main/artifacts/</url>
  </repository>
</repositories>
```
```
<dependencies>
  <dependency>
    <groupId>org.poseidondevs.jcommandapi</groupId>
    <artifactId>jcommandapi</artifactId>
    <version>1.0.1</version>
  </dependency>
</dependencies>
```
