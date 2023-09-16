package net.anvian.visualizerbookshelf.util;

import net.anvian.visualizerbookshelf.mixin.ChiseledBookshelfBlockInvoker;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class PlayerLookHelper {
    public static Pair<Book, ItemStack> getLookingAtBook(ChiseledBookshelfBlockEntity blockEntity) {
        Pair<Book, ItemStack> book = new Pair<>(Book.NONE, ItemStack.EMPTY);
        MinecraftClient client = MinecraftClient.getInstance();
        if (!(client.crosshairTarget instanceof BlockHitResult hit)) return book;
        // Get block entity from world if null
        assert client.world != null;
        if (blockEntity == null) {
            Optional<ChiseledBookshelfBlockEntity> blockEntityOptional = client.world.getBlockEntity(hit.getBlockPos(), BlockEntityType.CHISELED_BOOKSHELF);
            if (blockEntityOptional.isEmpty()) return book;
            blockEntity = blockEntityOptional.get();
        } else if (!hit.getBlockPos().equals(blockEntity.getPos())) return book;
        // Get hit position on the block and the slot
        Optional<Vec2f> hitPos = ChiseledBookshelfBlockInvoker.getHitPos(hit, blockEntity.getCachedState().get(HorizontalFacingBlock.FACING));
        if (hitPos.isEmpty()) return book;
        int slot = ChiseledBookshelfBlockInvoker.getSlotForHitPos(hitPos.get());
        return new Pair<>(Book.getBook(slot), blockEntity.getStack(slot));
    }

    public static List<Text> getBookText(ItemStack book) {
        List<Text> displayText = new ArrayList<>();
        displayText.add(book.getName());
        if (book.getItem() == Items.ENCHANTED_BOOK) {
            NbtCompound tag = book.getNbt();
            if (tag != null && tag.contains("StoredEnchantments")) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.fromNbt(tag.getList("StoredEnchantments", 10));
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    displayText.add(entry.getKey().getName(entry.getValue()));
                }
            }
        }
        return displayText;
    }
}
